(ns crawler.t-views
  (:use midje.sweet)
  (:require [crawler.views :as views]))

(facts "about `index-page`"
  (fact "Index page renders a link to the game" 
    (views/index-page) => "<!DOCTYPE html>\n<html><head><title>Walls Clojuring In</title><link href=\"/css/style.css\" rel=\"stylesheet\" type=\"text/css\"><link href=\"/flatui/css/bootstrap.css\" rel=\"stylesheet\" type=\"text/css\"><link href=\"/flatui/css/flat-ui.css\" rel=\"stylesheet\" type=\"text/css\"></head><body><div class=\"container\"><h1>Walls Clojuring In</h1><p>A dungeon crawler written in Clojure</p><a href=\"/room/0/0\">Start adventuring!</a></div></body></html>"
  )
)
