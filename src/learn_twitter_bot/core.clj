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
(def handle-last-id (atom {}))

(defn get-last-id 
  [handle]
  (get @handle-last-id handle))

(defn get-most-recent-status-id
  [handle]
  (->
    (statuses-user-timeline :oauth-creds my-creds
                            :params {:screen-name handle
                                     :exclude-replies true
                                     :count 1
                                     :include-rts false})
    (get :body)
    (first)
    (get :id)))

(defn get-most-recent-status-id-since
  [handle since-id]
  (->
    (statuses-user-timeline :oauth-creds my-creds
                            :params {:screen-name handle
                                     :exclude-replies true
                                     :count 1
                                     :since since-id
                                     :include-rts false})
    (get :body)
    (first)
    (get :id)))

(defn get-tweet-ids-to-fav
  [handles]
  (loop [handles-to-iter handles
         tweet-ids-to-fav {}]
    (if (empty? handles-to-iter)
      tweet-ids-to-fav
      (let [[curr-handle & rem-handles] handles-to-iter
            last-id (get-last-id curr-handle)]
        (if (nil? last-id)
          (let [new-last-id (get-most-recent-status-id curr-handle)]
            (recur rem-handles (assoc tweet-ids-to-fav
                                      curr-handle
                                      new-last-id)))
          (let [new-last-id (get-most-recent-status-id-since curr-handle
                                                             last-id)]
            (recur rem-handles (assoc tweet-ids-to-fav
                                      curr-handle
                                      new-last-id))))))))
(defn put-favourite
  [tweet-id]
  (println (str "Putting fav to " tweet-id))
  (try (favorites-create :oauth-creds my-creds
                         :params {:id tweet-id})
       (println "fav'd")
       (catch Exception e
         (println e))))

(defn put-favourites
  [latest-tweet-ids]
  (doseq [[handle tweet-id] latest-tweet-ids]
    (put-favourite tweet-id)))

(defn -main
  []
  (let [latest-tweet-ids (get-tweet-ids-to-fav @handles)]
    (put-favourites latest-tweet-ids)
    (swap! handle-last-id merge latest-tweet-ids)
    (println "done")))
