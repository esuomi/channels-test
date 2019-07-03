(ns nordeaopenbanking.channels
  (:require [clojure.core.async :refer [chan put! take! >! <! <!! buffer dropping-buffer sliding-buffer timeout close! alts! poll! go go-loop alt!]]
            [throttler.core :refer [pipe throttle-chan throttle-fn chan-throttler]]))

(def ^:private global-api-throttler 
  "Shared API rate limiter accumulated for all APIs"
  (chan-throttler 500000 :day 499999)) ; TODO: these are 100x the actual limits as the real limits are still only 3,47222... calls per minute

(defn create-in [n]
  (chan n))

(defn create-out [in items per-unit]
  (-> in (throttle-chan items per-unit (dec items)) (global-api-throttler)))