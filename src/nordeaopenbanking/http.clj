(ns nordeaopenbanking.http
    (:require [clojure.core.async :refer [put! <! go]]
              [org.httpkit.client :as http]))

(defn timestamp [] (.toString (java.time.LocalTime/now)))

; https://www.http-kit.org/client.html#options
(def ^:private options 
    {:timeout 200             ; ms
     :basic-auth ["user" "pass"]
     :query-params {:param "value" :param2 ["value1" "value2"]}
     :user-agent "User-Agent-string"
     :headers {"X-Header" "Value"}
     :keepalive 120000})

(defn- http-handler 
  [out]
  (fn [{:keys [status headers body error opts]}]
    (if error
      (put! out (str (timestamp) " " (:url opts) " Failed, exception is " error))
      (put! out (str (timestamp) " " (:url opts) " Async HTTP GET: " status)))))

(defn get 
  [out url]
  (http/get url options (http-handler out)))