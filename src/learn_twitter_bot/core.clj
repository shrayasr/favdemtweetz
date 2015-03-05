(ns learn-twitter-bot.core
  (:use
    [twitter.oauth]
    [twitter.callbacks]
    [twitter.callbacks.handlers]
    [twitter.api.restful])
  (:import
    (twitter.callbacks.protocols SyncSingleCallback))
  (:gen-class))

(def my-creds (make-oauth-creds "7nRS6v8GqqWm3r7s4NsnCC7fX"
                                "5ivF3wwvgPEbfB8FvJdl1c6X5tchJxsICd7hHDUiiryhVMIUN9"
                                "46169732-tKiDKTaQNFBce04bGUuVku3QaNzdLNT6v3xTl2sNA"
                                "DypaSi6CichUmjwSNMcsm2R8wyxkD675JkbYZDazUZ2SV"))

(defn -main
  []
  (println (users-show :oauth-creds my-creds :params { :screen-name "shrayasr" })))
