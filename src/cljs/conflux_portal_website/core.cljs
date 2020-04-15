(ns conflux-portal-website.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [breaking-point.core :as bp]
   [conflux-portal-website.events :as events]
   [conflux-portal-website.routes :as routes]
   [conflux-portal-website.views :as views]
   [conflux-portal-website.config :as config]
   ))


(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn init []
  (routes/app-routes)
  (re-frame/dispatch-sync [::events/initialize-db])
  (re-frame/dispatch-sync [::bp/set-breakpoints
                           {:breakpoints [:mobile
                                          768
                                          :tablet
                                          992
                                          :small-monitor
                                          1200
                                          :large-monitor]
                            :debounce-ms 166}])
  (dev-setup)
  (mount-root))
