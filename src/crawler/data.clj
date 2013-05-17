(ns crawler.data
  (:use [hiccup core page]))

; Layout of the rooms. These are all bitmasks, from top clockwise
; bits are:
;  1 - exit to north
;  2 - exit to east
;  4 - exit to south
;  8 - exit to west
(def rooms [
  [4  4   4  6  8  2  14 12]
  [7  11  9  7  10 10 15 13]
  [3  10  10 11 8  2  9  1]
])
