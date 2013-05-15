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

(defn room-page [x y]
  (def mask ((rooms y) x))

   (def westlink (str "/room/" x "/" y))

  (html5
    [:head
      [:title "Walls Clojuring In: A Room"]
      (include-css "/css/style.css")]
    [:body
      [:h1 "A room"]
      [:p "You are in a room with mask = " mask "  at (" x ", " y "). Exits are:"]
      [:p (get-exits mask x y)]
    ]))
