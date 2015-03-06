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

(def handles (atom ["raghothams" "argvk"]))
(def handle-last-id (atom {"argvk" 1}))

(defn get-last-id 
  [handle]
  (get @handle-last-id handle))

(defn get-first-tweet-id-from-timeline 
  [handle]
  1)

(defn get-tweet-id-since
  [last-id]
  2)

(defn get-tweet-ids-to-fav
  [handles]
  (loop [handles-to-iter handles
         tweet-ids-to-fav []]
    (if (empty? handles-to-iter)
      tweet-ids-to-fav
      (let [[curr-handle & rem-handles] handles-to-iter
            last-id (get-last-id curr-handle)]
        (if (nil? last-id)
          (recur rem-handles (conj tweet-ids-to-fav 
                                   (get-first-tweet-id-from-timeline curr-handle)))
          (recur rem-handles (conj tweet-ids-to-fav
                                   (get-tweet-id-since last-id))))))))

(defn -main
  []
  (println (get-last-id "raghothams"))
  (println (get-last-id "argvk"))
  (println (get-tweet-ids-to-fav @handles)))
  ;(println (lists-members :oauth-creds my-creds
  ;            :params {:list_id "197805222"})))
  ;(favorites-create :oauth-creds my-creds
  ;                   :params {:id "572630868549758976"}))
  ;(println (statuses-user-timeline :oauth-creds my-creds
  ;                                 :params {:screen-name "raghothams"
  ;                                          :exclude-replies true
  ;                                          :count 1
  ;                                          :include-rts false})))
