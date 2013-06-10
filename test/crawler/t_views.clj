(ns crawler.t-views
  (:use midje.sweet)
  (:require [crawler.views :as views]))

(facts "about `index-page`"
  (fact "Index page renders a link to the game" 
    (views/index-page) => (contains "Start adventuring!")
  )
)
