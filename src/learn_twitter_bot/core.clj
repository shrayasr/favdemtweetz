(ns learn-twitter-bot.core
  (:use
    [twitter.oauth]
    [twitter.callbacks]
    [twitter.callbacks.handlers]
    [twitter.api.restful])
  (:import
    (twitter.callbacks.protocols SyncSingleCallback))
  (:gen-class))

(def my-creds (make-oauth-creds "phk6yVKreqqWYwkrKrV2LtXlN"
                                "48eFKSQteSHuDRr2d00cvA5BPQLlaSRIBmN0TE3J8BZvgUtZrR"
                                "3063370765-T7TlE9bfO6SEaV6dvvGV6mXltOYRnLP6sBvVrNM"
                                "EmSeWB17z8qyPBKwbWoNsrUM7TDQciYTJga3ehAHWY7ba"))

(defn -main
  []
  ;(println (lists-members :oauth-creds my-creds
  ;            :params {:list_id "197805222"})))
  (favorites-create :oauth-creds my-creds
                     :params {:id "550993690659794945"})
  (println "done"))
