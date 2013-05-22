(ns crawler.t-data
  (:use midje.sweet)
  (:require [crawler.data :as data]))

(facts "about `rooms`"
  (fact "Top left room has exit south only" 
    (first (first data/rooms)) => 4
  )
  (fact "Bottom right room has exit north and also an exit"
    (last (last data/rooms)) => 17
  )
  (fact "Data has a bitmask for exits"
    data/bmexit => 16

  )
)
