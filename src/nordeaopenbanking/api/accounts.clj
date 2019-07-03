(ns nordeaopenbanking.api.accounts
  (:require [clojure.core.async :refer [chan put! <! go]]
            [nordeaopenbanking.api.authorization :as auth]
            [nordeaopenbanking.channels :as channels]              
            [nordeaopenbanking.http :as http]))

(def ^:private accounts-api-chan-in  (channels/create-in 10))
(def ^:private accounts-api-chan-out (channels/create-out accounts-api-chan-in))

(defn accounts-api-call [out]
  (put! accounts-api-chan-in {:url "http://www.example.com/accounts"})  ; TODO: check if true/false to see if channel is closed
  (go
    (let [{:keys [url]} (<! accounts-api-chan-out)
          ; lets fake not having authorization by calling it
          authorized (-> (chan) (auth/authorize) (<!))]
      (println authorized)
      (http/get out url)))
  out)
