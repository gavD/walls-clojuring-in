(ns crawler.views
  (:use [hiccup core page]))

; Layout of the rooms. These are all bitmasks, from top clockwise
; bits are:
;  1 - exit to north
;  2 - exit to east
;  4 - exit to south
;  8 - exit to west
(def rooms [
    [4  4   4  6  8  2  14 12]
    [7  11  9  7  10 10 15 13]
    [3  10  10 11 8  2  9  1]
])


; Function to render the homepage
(defn index-page []
  (html5
    [:head
      [:title "Walls Clojuring In"]
      (include-css "/css/style.css")
      (include-css "/flatui/css/bootstrap.css")
      (include-css "/flatui/css/flat-ui.css")
    ]
    [:body
      [:div {:class "container"}
        [:h1 "Walls Clojuring In"]
        [:p "A dungeon crawler written in Clojure"]
        [:a {:href "/room/0/0"} "Start adventuring!"]]]))
; TODO make the href pull from the route rather than being hard coded

; Function to render a room as user explores
(defn room-page [x y]

  (def mask ((rooms y) x))

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

                  ; Show player character
                  (if (and (= colidx x) (= rowidx y))
                      (str "X")
                      (str " ")
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

    (apply str (map-indexed show-row rooms))
  )

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
            [:p "You are in a room"]
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
    ]]))
