(ns crawler.views
  (:require [crawler.data :as data])
  (:use [hiccup core page])
  (use [ring.util.response :only [redirect]]
    router.core
  )
)

; Snippets of HTML
(def header
    [:head
      [:title "Walls Clojuring In"]
      (include-css "/css/style.css")
      (include-css "/flatui/css/bootstrap.css")
      (include-css "/flatui/css/flat-ui.css")
    ]
)
(def footer
  [:footer
    [:hr]
    "Walls Clojuring In - a barely functional dungeon crawler!"
  ]
)

(defn index-page
  "Function to render the homepage"
  []
  (html5
    header
    [:body
      [:div {:class "container"}
        [:h1 "Walls Clojuring In"]
        [:p "A dungeon crawler written in Clojure"]
        [:a {:href (url :room :x 0 :y 0)} "Start adventuring!"]]
    footer
]))

(defn complete-page
  "For when the user has found the exit"
  []
  (html5
    header
    [:body
      [:div {:class "container"}
        [:h1 "A winner is you!"]
        [:p "Well done, you escaped the dungeon!"]
        [:a {:href (url :home)} "Back to the homepage!"]]
    footer
    ]))

(defn room-page
  "Render a room as user explores"
  [x y]

  (def mask ((data/rooms y) x))

  (defn get-cardinal
    "See if cardinal identified by bit is available in the room with mask mask"
    [mask bit label xMove yMove]
    (if (not= (bit-and mask bit) 0)
      (html [:li {:id (str "cardinal" label)} [:a {:class "btn btn-primary" :href (str (url :room :x (+ x xMove) :y (+ y yMove)))} (str label)] ] )
      (html [:li {:id (str "cardinal" label)} [:a {:class "btn btn-danger" :href "#" } (str label)] ])
    )
  )

  (defn get-exits
    "Interrogate mask and work out which exits it has"
    [mask]
    (html
      [:ul {:class "compass"}
         (get-cardinal mask 1 "North" 0 -1)
         (get-cardinal mask 8 "West" -1 0)
         (get-cardinal mask 2 "East" 1 0)
         (get-cardinal mask 4 "South" 0 1)
      ]
    )
  )

  (defn show-row
    "Render a row of the map as HTML"
    [rowidx row]

    (defn show-datum
      "Show an individual room on the map"
      [colidx datum]
      (html
        [:td
          [:pre
            (apply str
              ; top of box
              (if (= rowidx 0)
                ; only show left wall if first col
                (if (= colidx 0)
                  (if (not= (bit-and datum 1) 0) (str "+ +\n") (str "+-+\n"))
                  (if (not= (bit-and datum 1) 0) (str " +\n") (str "-+\n"))
                )
              )
              ; left side of box - only show left wall if first col
              (if (= colidx 0)
                (if (not= (bit-and datum 8) 0) (str " ") (str "|"))
              )

              ; Draw the current centre of the current room
              (if (and (= colidx x) (= rowidx y))
                ; Show player character if s/he is in this room
                (str "X")

                ; If the current tile has the End level bitmask,
                ; display the tile
                (if(not= (bit-and datum 16) 0)
                  (str "O")
                  (str " ")
                )
                ;; TODO this needs refactoring
              )

              ; right side of box
              (if (not= (bit-and datum 2) 0) (str " \n") (str "|\n"))

              ; base of box - only show left wall if first col
              (if (= colidx 0)
              (if (not= (bit-and datum 4) 0) (str "+ +") (str "+-+"))
                (if (not= (bit-and datum 4) 0) (str " +") (str "-+"))
              )
            )
          ]
        ]
      )
    )

    (html
      [:tr (apply str (map-indexed show-datum row))]
    )
  )

  ; Function to 
  (defn show-map
    "render the playing area as HTML"
    []
    (apply str (map-indexed show-row data/rooms))
  )

  (if(= (bit-and ((data/rooms y) x) 16) 0)
    ; render the HTML for the page
    (html5
      [:head
        [:title "Walls Clojuring In: A Room"]
        (include-css "/flatui/css/bootstrap.css")
        (include-css "/flatui/css/flat-ui.css")
        (include-css "/css/style.css")]
      [:body {:class "palette-night-dark"}
        [:div {:class "container"}
          [:div {:class "row"}
            [:div {:class "span4 description"}
              [:h1 "A room"]
              [:p "You are in a room at " x ", " y]
            ]
            [:div {:class "span4 controls"}
              [:p (get-exits mask)]
            ]
            [:div {:class "span4 map"}
              [:h2 "Map of area"]
              [:table {:class "map"} (show-map)]
            ]]
          [:div {:class "row"}
            [:div {:class "span12"} "<a href=\"/\">Back to home</a>"]
          ]]
      (include-js "/js/jquery-1.9.1.min.js" "/js/controls.js")
      footer])

  ; If the end of the level has been reached...
    (redirect (url :win))
  )
)
 
