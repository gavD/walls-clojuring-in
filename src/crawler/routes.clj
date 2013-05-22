(ns crawler.routes
  (:use compojure.core
        crawler.views
        router.core
        [hiccup.middleware :only (wrap-base-url)])
  (:require
            [compojure.route :as route]
            [compojure.handler :as handler]
            [compojure.response :as response]))

;; Using router library by @rodnaph
(set-routes!
  {:home            "/"
   :win             "/win"
   :room            "/room/:x/:y"})

(defroutes main-routes
  (GET (rte :home) [] (index-page))
  (GET (rte :win) [] (complete-page))
  (GET [(rte :room) :x #"[0-9]+" :y #"[0-9]+"] [x y]
    (room-page 
       (Integer/parseInt x)
       (Integer/parseInt y)
    )
  )

  (route/resources "/")
  (route/not-found "Page not found"))

(def app
  (-> (handler/site main-routes)
      (wrap-base-url)))
