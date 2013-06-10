(ns crawler.t-views
  (:use midje.sweet)
  (:require [crawler.views :as views]))

(facts "about views"

  (fact "Footer contains links"
    views/footer => (contains "Walls Clojuring In - a barely functional dungeon crawler!")
  )
)
