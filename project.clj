(defproject compojure-crawler "0.0.1-SNAPSHOT"
  :description "Dungeon crawler in Clojure"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [compojure "1.1.1"]
                 [hiccup "1.0.0"]
                 [router "0.1.0"]]
  :plugins [[lein-ring "0.7.1"]]
  :dev-dependencies [[midje "1.5.0"  :exclusions [org.clojure/clojure]]
                     [lein-midje "3.0.0"]]
  :ring {:handler crawler.routes/app}
  :profiles {:dev {:dependencies [[midje "1.5.0"]]
                   :plugins [[lein-midje "3.0.0"]]
  }}
)
