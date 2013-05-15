(defproject compojure-crawler "0.1.0"
  :description "Dungeon crawler in Clojure"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [compojure "1.1.1"]
                 [hiccup "1.0.0"]]
  :plugins [[lein-ring "0.7.1"]]
  :ring {:handler compojure.crawler.routes/app})
