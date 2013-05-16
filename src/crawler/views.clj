(ns crawler.views
  (:use [hiccup core page]))

(def rooms [
    [4  4   4]
    [7  11  9]
    [3  10  8]
])

(defn index-page []
  (html5
    [:head
      [:title "Walls Clojuring In"]
      (include-css "/css/style.css")]
    [:body
      [:h1 "Walls Clojuring In"]
      [:p "A dungeon crawler written in Clojure"]
      [:a {:href "/room/0/0"} "Start adventuring!"]]))
; TODO make the href pull from the route rather than being hard coded

; TODO http://clojuredocs.org/clojure_core/clojure.core/map-indexed to communicate player position
; See notes at https://gist.github.com/owainlewis/5584722

;(show-row [4 5 6 2])
;(show-datum 4)
;(show-map 3 2)

(defn room-page [x y]

  (def mask ((rooms y) x))

  (defn get-exits [mask x y]
    (html
      [:ul
        (if (not= (bit-and mask 1) 0) (html [:li [:a {:href (str "/room/" x "/" (- y 1))} "North"]]))
        (if (not= (bit-and mask 2) 0) (html [:li [:a {:href (str "/room/" (+ x 1) "/" y)} "East"]]))
        (if (not= (bit-and mask 4) 0) (html [:li [:a {:href (str "/room/" x "/" (+ y 1))} "South"]]))
        (if (not= (bit-and mask 8) 0) (html [:li [:a {:href (str "/room/" (- x 1) "/" y)} "West"]]))
      ]
    )
  )

  (defn show-map []

    (defn show-row[rowidx row]

      (defn show-datum[colidx datum]
        (html
          [:td 
              (html [:pre
                (apply str
                  ; TODO how if/else?
  
                  ; top of box
                  (if (not= (bit-and datum 1) 0) (str "+ +\n"))
                  (if (= (bit-and datum 1) 0) (str "+-+\n"))
  
                  ; left side of box
                  (if (not= (bit-and datum 8) 0) (str " "))
                  (if (= (bit-and datum 8) 0) (str "|"))
  
                  (if (and (= colidx x) (= rowidx y))
                      (str "x")
                      (str " ")
                  )
  
                  ; right side of box
                  (if (not= (bit-and datum 2) 0) (str " \n"))
                  (if (= (bit-and datum 2) 0) (str "|\n"))
  
                  ; base of box
                  (if (not= (bit-and datum 4) 0) (str "+ +"))
                  (if (= (bit-and datum 4) 0) (str "+-+"))
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
  (html5
    [:head
      [:title "Walls Clojuring In: A Room"]
      (include-css "/css/style.css")]
    [:body
      [:h1 "A room"]
      [:p "You are in a room with mask = " mask "  at (" x ", " y "). Exits are:"]
      [:p (get-exits mask x y)]
      [:h2 "Map of area"]
      [:table (show-map)]
    ]))
