(ns nordeaopenbanking.api.authorization
    (:require [clojure.core.async :refer [put! <! go]]
              [nordeaopenbanking.channels :as channels]              
              [nordeaopenbanking.http :as http]))

(defn timestamp [] (.toString (java.time.LocalTime/now)))

(def ^:private authorize-api-chan-in  (channels/create-in 10))
(def ^:private authorize-api-chan-out (channels/create-out authorize-api-chan-in 10 :minute))

(defn authorize [out]
  (put! authorize-api-chan-in {:url "http://www.example.com/authorize"})  ; TODO: check if true/false to see if channel is closed
  (go
    (let [{:keys [url]} (<! authorize-api-chan-out)]
      (http/get out url)))
  out)

(defn exchange [] (comment "TODO"))
(defn refresh [] (comment "TODO"))
