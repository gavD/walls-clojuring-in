(ns compojure.crawler.views
  (:use [hiccup core page]))

(defn index-page []
  (html5
    [:head
      [:title "Hello World"]
      (include-css "/css/style.css")]
    [:body
      [:h1 "Walls Clojuring In"]
      [:p "A dungeon crawler written in Clojure"]]))
