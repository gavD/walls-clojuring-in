(ns crawler.routes
  (:use compojure.core
        crawler.views
        [hiccup.middleware :only (wrap-base-url)])
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [compojure.response :as response]))

(defroutes main-routes
  (GET "/" [] (index-page))
  (GET ["/room/:x/:y" :x #"[0-9]+" :y #"[0-9]+"] [x y]
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
