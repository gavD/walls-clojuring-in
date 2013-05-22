(ns crawler.views
  (:require [crawler.data :as data])
  (:use [hiccup core page])
  (use [ring.util.response :only [redirect]]) 
)

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

; Function to render the homepage
(defn index-page []
  (html5
    header
    [:body
      [:div {:class "container"}
        [:h1 "Walls Clojuring In"]
        [:p "A dungeon crawler written in Clojure"]
        [:a {:href "/room/0/0"} "Start adventuring!"]]
    footer
]))

(defn complete-page []
  (html5
    header
    [:body
      [:div {:class "container"}
        [:h1 "A winner is you!"]
        [:p "Well done, you escaped the dungeon!"]
        [:a {:href "/"} "Back to the homepage!"]]
    footer
    ]))
; TODO make the href pull from the route rather than being hard coded

; Function to render a room as user explores
(defn room-page [x y]

  (def mask ((data/rooms y) x))

  (defn get-cardinal [mask bit label xMove yMove]
    (if (not= (bit-and mask bit) 0)
      (html [:li {:id (str "cardinal" label)} [:a {:class "btn btn-primary" :href (str "/room/" (+ x xMove) "/" (+ y yMove))} (str label)] ] )
      (html [:li {:id (str "cardinal" label)} [:a {:class "btn btn-danger" :href "#" } (str label)] ])
    )
  )

  (defn get-exits [mask x y]
    (html
      [:ul {:class "compass"}
         (get-cardinal mask 1 "North" 0 -1)
         (get-cardinal mask 8 "West" -1 0)
         (get-cardinal mask 2 "East" 1 0)
         (get-cardinal mask 4 "South" 0 1)
      ]
    )
  )

  ; Function to render the playing area as HTML
  (defn show-map []

    ; Function to show a row of thmap
    (defn show-row[rowidx row]

      ; Function to show an individual room on the map
      (defn show-datum[colidx datum]
        (html
          [:td
              (html [:pre
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
            ])
          ]
        )
      )

      (html
        [:tr (apply str (map-indexed show-datum row))]
      )
    )

    (apply str (map-indexed show-row data/rooms))
  )

  (if(= (bit-and ((data/rooms y) x) 16) 0)
 ; render the HTML for the page
  (html5
    [:head
      [:title "Walls Clojuring In: A Room"]
      (include-css "/flatui/css/bootstrap.css")
      (include-css "/flatui/css/flat-ui.css")
      (include-css "/css/style.css")
    ]
    [:body {:class "palette-night-dark"}
      [:div {:class "container"}
        [:div {:class "row"}
          [:div {:class "span4 description"}
            [:h1 "A room"]
            [:p "You are in a room at " x ", " y]
          ]
          [:div {:class "span4 controls"}
            [:p (get-exits mask x y)]
          ]
          [:div {:class "span4 map"}
            [:h2 "Map of area"]
            [:table {:class "map"} (show-map)]
          ]
        ]
        [:div {:class "row"}
          [:div {:class "span12"} "<a href=\"/\">Back to home</a>"]
        ]
    ]
    (include-js "/js/jquery-1.9.1.min.js" "/js/controls.js")
    footer
  ])

    ; If the end of the level has been reached...
  ;  (html5 [:p "A winner is you!" ])
    (redirect "/win")
    ; TODO perhaps redirect?
  )
)
 
