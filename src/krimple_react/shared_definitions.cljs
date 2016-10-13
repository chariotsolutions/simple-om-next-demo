(ns krimple-react.shared-definitions
  (:require
   [cljs.core.async :refer [<! >! put! chan timeout]]
   [clojure.spec :as s]
   [om.next :as om :refer-macros [defui]])
  (:require-macros
   [cljs.core.async.macros :refer [go go-loop]]))

(def videos
  "List of available videos (hard-coded at the moment)"
  [{:id "185336500"
    :title "Van Neumann Machine"
    :description "Something about VNMs"}
   {:id "184394491"
    :title "Why does Functional Programming Even Matter?"
    :description "Sujan Kapadia"}
   {:id "184068444"
    :title "Sea Child"
    :description "Random video"}
   {:id "43418419"
    :title "Effective Scala"
    :description "Points, sharp edges, rough patches"}])

(declare reconciler log-this!)

(def app-state
  "All application state:

  - A title
  - A place to put the *Vimeo Player*
  - The ID of the video to show first
  - A \"database\" of videos"
  {:title          "Chariot Video Stream (Om.Next)"
   :sort-order :unsorted
   :videos []
   :media-player   {:player-element-id "player-place"}
   :selected-video {:id "NO VIDEO"}
   :logging-enabled true})

(defmulti read
  "Return data from the application state, depending on the key given.
  By default, do a simple lookup, but if need be we can write methods
  for arbitarily complex queries."
  (fn [env key params] key))

(defn get-videos
  "Return the videos"
  [state k]
  (let [st @state]
    (into [] (map #(get-in st %)) (get st k))))

;; Look up data by key by default.
;;
;; Return `:not-found` in case it isn't found!
(defmethod read :default
  [{:keys [state query] :as env} k params]
  (let [{:keys [logging-enabled]} @state]
    (log-this! state (str "reading :default"
                          "\nst: " @state
                          "\nq: " query
                          "\nk: " k
                          "\np: " params))
    (let [st @state]
      (if-let [[_ value] (find st k)]
        (let [result value]
          (log-this! state "result: " result)
          {:value value})
        (if-let [query (:query env)]
          (let [result (om/db->tree query st st)]
            (log-this! state "q-result: " result)
            {:value result})
          (do
            (log-this! state "result: :not-found")
            {:value :not-found}))))))

#_(defmethod read :videos
    "Not working as expected"
  [{:keys [state ast query] :as env} k params]
  (log-this! state (str "Reading :videos"
                "\nst: " @state
                "\nq: " query
                "\na: " ast
                "\nk: " k
                "\np: " params))
  (let [result (get-videos state k)]
    (log-this! state "result: " result)
    {:value result}))

(defn is-om-link?
  "Is this query-element a top-level \"link\"?"
  [q-elem]
  (and (vector? q-elem)
       (= 2 (count q-elem))
       (= '_ (second q-elem))))

(defn is-om-ident?
  "Is this query-element an Om-Next/React key?"
  [q-elem]
  (and (vector? q-elem)
       (= 2 (count q-elem))
       (not= '_ (second q-elem))))

(defn is-om-ident-or-link?
  "Is this an internal key of some sort?"
  [q-elem]
  (or (is-om-ident? q-elem)
      (is-om-link? q-elem)))

(defn extract-video-id
  "Extract video-id from item, whether a link
  or a map"
  [video-item]
  (if (is-om-ident? video-item)
    (second video-item)
    (:id video-item)))

(defn current-video-ids
  "Return video IDs in the current state"
  [])

(defmethod read :videos
  [{:keys [query state ast]} k params]
  (log-this! state (str "Reading :videos"
                  "\nst: " @state
                  "\nq: " query
                  "\na: " ast
                  "\nk: " k
                  "\np: " params))
  (let [st @state
        value-result (om/db->tree query (get st k) st)]
    (log-this! state "Results of reading :videos\n\t:value \"" value-result "\"\n\t:vimeo \"" ast "\"}")
    {:value value-result
     :vimeo true ;ast
     }))

(defmethod read :media-list
  [{:keys [query parser ast state] :as env} k params]
  (log-this! state (str "Reading :media-list"
                "\nst: " @state
                "\nq: " query
                "\na: " ast
                "\nk: " k
                "\np: " params))
  (let [st @state
        value-result (parser env query)]
    (log-this! state "Results of reading :media-list\n\t" value-result "\n\t" ast)
    {:value value-result}))

(defmethod read :media-player
  [{:keys [query state]} k _]
  (let [st @state]
    {:value (om/db->tree query (get st k) st)}))

#_(defmethod read :selected-video
  [{:keys [state] :as env} k params]
  (let [st @state]
    {:value (get-in st (get st k))}))

(defmulti mutate
  "Update application data: keys will be symbols. (Conventionally:
  keywords are used for `read` operations, to look like structure
  accesses, and symbols are used for `mutate` operations"
  om/dispatch)

;; Update the `:selected-video` value using the
;; `:selected-video` parameter
(defmethod mutate 'do/select-video!
  [{:keys [state] :as env} _ {:keys [selected-video] :as params}]
  {:action #(swap! state assoc
                   :selected-video selected-video)
   :value {:keys [:selected-video]}})

(defmethod mutate 'do/toggle-logging!
  [{:keys [state] :as env} _ _]
  {:action #(swap! state update :logging-enabled (fn [x] (not x)))
   :value {:keys [:logging-enabled]}})

(defn get-the-state-atom
  "Given a component, or a reconciler, or the state atom, return the state atom.

        (swap! (get-the-state-atom reconciler) assoc :logging-enabled true)    
"
  [x]
  (as-> x thing
    ;; If `thing` is a component, get its reconciler
    (cond-> thing (om/component? thing) (om/get-reconciler))
    ;; If 'thing` is a reconciler, pull the state atom out of it
    (cond-> thing (om/reconciler? thing) (get-in [:config :state] {}))))

(defn get-the-state
  "Given a component, or a reconciler, a state atom, or the state, return the state.

        (when (:logging-enabled (get-the-state reconciler))
          (.log js/conaole \"we made it this far\"))
"
  [x]
  (as-> x thing
    ;; Get the state atom from thing (assuming its there)
    (get-the-state-atom thing)
    ;; If what we have is an atom, deref it; or return it
    (cond-> thing (instance? Atom thing) (deref))))

(defn log-this!
  [x & args]
  (let [app-state (get-the-state x)]
    (if (get app-state :logging-enabled true)
      (try
        (apply println args)
        (catch js/Object o)))))

(let [known-videos (atom [])]
  (defn vimeo-known-video-reset!
    []
    (reset! known-videos []))
  (defn vimeo-remote
    "Incrementally add videos to the list of available videos"
    [query-maybe cb]
    (println "****************************************")
    (println "****************************************")
    (println "****************************************")
    (println "****************************************")
    (println "****************************************")
    (println "****************************************")
    (log-this! reconciler "****************************************")
    (log-this! reconciler "vimeo-remote: qm:" query-maybe)
    (log-this! reconciler "****************************************")
    (go
      (<! (timeout 5000))
      (loop [[video & vs] videos]
        (let [id-set (set (map :id @known-videos))]
          (log-this! reconciler "id-set:" id-set)
          (when-not (contains? id-set (:id video))
            (log-this! reconciler "Providing " video)
            (swap! known-videos conj video)
            (cb {:videos @known-videos})))
        (if (seq vs)
          (recur vs))))))

(defn my-sender
  "
  Function dispatching calls to other systems

  After a query returns, call callback `cb` with the results

  See [Awkay on Remote Fetch in his Om Next Tutorial](https://awkay.github.io/om-tutorial/#!/om_tutorial.G_Remote_Fetch)
  for details. (This is the most incredibly *helpful* material I've seen on **Om Next Remotes**; 
  I'm not sure if it was this complete when I worked through it months ago, or
  if I just thoughtlessly stopped working through it, and got back to
  the project I was working on at the time.)
"
  [map-with-remote-queries cb]
  (log-this! reconciler "****************************************\nremotes:" map-with-remote-queries
           "\n****************************************")
  (when-let [{:keys [vimeo]} map-with-remote-queries]
    (log-this! reconciler "Sending off to vimeo-remote")
    (vimeo-remote vimeo cb)))

(def my-parser
  "Break parser out so I can see it (for development)"
  (om/parser {:read read :mutate mutate}))

;; The Om Reconciler ties state and the parser together
(defonce reconciler
  (om/reconciler {:state app-state :parser my-parser :send my-sender
                  :remotes [:vimeo]}))

(s/def ::boolean #(or (= true %)
                      (= false %)))

(defmethod mutate 'do/set-logging!
  [{:keys [state]} _ {:keys [logging-enabled]}]
  {:action #(swap! state assoc :logging-enabled logging-enabled)
   :value {:keys [:logging-enabled]}})

(defn set-logging!
  "Turn logging on or off"
  [desired-logging-state]
  (if (s/valid? ::boolean desired-logging-state)
    (om/transact! reconciler `[(do/set-logging! {:logging-enabled ~desired-logging-state})
                               :logging-enabled])
    (s/explain ::boolean desired-logging-state)))

(defn set-video!
  "Set a particular video as selected"
  [n]
  (let [videos (:videos (get-the-state reconciler))]
    (if (contains? videos n)
      (om/transact! reconciler `[(do/select-video! {:selected-video ~(videos n)})
                                 :selected-video])
      (println "Video out of range"))))
