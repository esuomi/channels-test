(ns channel-test.core
  (:require [clojure.core.async :refer [chan <!!]]
            [nordeaopenbanking.api.accounts :as accounts])
  (:gen-class))

; start REPL (eg. `lein repl`) and run
; (time (-> (chan) (accounts/accounts-api-call) <!!))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
