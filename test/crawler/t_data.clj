(ns crawler.t-data
  (:use midje.sweet)
  (:require [crawler.data :as data]))

(facts "about `rooms`"
  (fact "Top left room has exit south only" 
    (first (first data/rooms)) => 4
  )
  (fact "Bottom right room has exit north only"
    (last (last data/rooms)) => 1
  )
)
