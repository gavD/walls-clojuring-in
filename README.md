Walls Clojuring In
==================

A simple dungeon crawler implemented in Clojure.

Objective
---------

Find the dude who is hiding!

Installation and running
------------------------

Download the project dependencies with:

    lein deps

Now you can start a development web server with:

    lein ring server

Or you can compile the project into a war-file ready for deployment to
a servlet container:

    lein ring uberwar

Running unit tests
------------------

Unit tests are implemented in Midje. Run them all with:

    lein midje

If you're developing, you can run:

    lein midje autotest

Then they will run on every change.

Implementation details
----------------------

Room shapes are created using a bitmask of the "walls"

    +--1--+
    |     |
    8     2
    |     |
    +--4--+

So, here are some example room shapes:

    +-+      Exit West only
     8|     8
    +-+

    + +      Exits North, South, East
    |7     1 + 2 + 4 = 7
    + +

    + +     Exits North and West
     6|     1 + 2 + 4 = 7
    +-+

    +-+        Exits East and West
     5        1 + 4 = 5
    +-+

    +-+     Exit South only
    |4|     4 = 4
    + +

    + +         Exits North and East
    |3        1 + 2 = 3
    +-+

    +-+        Exit East only
    |2        2 = 2
    +-+

    + +        Exit north
    |1|        1 = 1 
    +-+

    +-+       
    |0|        No exits
    +-+

 rooms are stored in a 2D array

Game over
---------

Levels are complete when you land on a tile that has the "end level" bitmask

    16 End level bitmask

Monsters
--------

Fitting into the walls bitmask, monsters are on:

    32  Troll
    64  Goblin

