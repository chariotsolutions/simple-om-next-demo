(ns krimple-react.shared-definitions
  (:require
   [cljs.core.async :refer [<! >! put! chan timeout]]
   [clojure.spec :as s]
   [om.next :as om :refer-macros [defui]])
  (:require-macros
   [cljs.core.async.macros :refer [go go-loop]]))

(def server-video-list
  "List of available videos (hard-coded at the moment)"
  [{:video/id "185336500"
    :video/title "Van Neumann Machine"
    :video/description "Something about VNMs"
    :video/related-videos []}
   {:video/id "179160976"
    :video/title "Adventures in Elm:video/ Events, Reproducibility, and Kindness"
    :video/description (str "What do you get when you combine strict"
                            " functional programming with heavy user interaction?"
                            " Challenges, and"
                            " unexpected freedoms. Elm is a purely functional language"
                            " for the browser. It compiles…")
    :video/related-videos [[:videos/by-id "184394491"]
                           [:videos/by-id "43418419"]]}
   {:video/id "184394491"
    :video/title "Why does Functional Programming Even Matter?"
    :video/description "Sujan Kapadia"
    :video/related-videos [[:videos/by-id "179160976"]
                           [:videos/by-id "43418419"]]}
   {:video/id "167447746"
    :video/title "Pitfalls in Technology Selection"
    :video/description (str "Technology is an ever-changing arena where it"
                            " seems that everything is the one perfect"
                            " solution for all your problems. When someone shows"
                            " you the Next Big Thing, how can you"
                            " be sure that it will work?")}
   {:video/id "184068444"
    :video/title "Sea Child"
    :video/description "Random video"}
   {:video/id "43418419"
    :video/title "Effective Scala"
    :video/description "Points, sharp edges, rough patches"
    :video/related-videos [[:videos/by-id "184394491"]
                           [:videos/by-id "179160976"]]}])

(declare reconciler)

(def app-state
  "All application state:

  - A title
  - A place to put the *Vimeo Player*
  - The ID of the video to show first
  - A \"database\" of videos"
  {:app/title        "Chariot Video Stream (Om.Next)"
   :app/videos       []
   :app/media-player {:player-element-id "player-place"}
   :selected-video   [:videos/by-id "NO VIDEO"]
   :videos/by-id     {"NO VIDEO" {:video/id          "NO VIDEO"
                                  :video/title       "No Video selected or available"
                                  :video/description "Videos have not yet been loaded, or none are available"}}
   :logging-enabled  false})

(defmulti read
  "Return data from the application state, depending on the key given.
  By default, do a simple lookup, but if need be we can write methods
  for arbitarily complex queries."
  (fn [env key params] key))

;; Look up data by key by default.
;;
;; Return `:not-found` in case it isn't found!
(defmethod read :default
  [{:keys [state query] :as env} k params]
  (let [st @state]
    (if-let [[_ value] (find st k)]
      {:value value}
      (if-let [query (:query env)]
        {:value (om/db->tree query st st)}
        {:value :not-found}))))

(defmethod read :app/videos
  [{:keys [query state ast]} k params]
  (let [st @state
        value-result (om/db->tree query (get st k) st)]
    {:value value-result}))

(defmethod read :app/media-list
  [{:keys [query parser ast state] :as env} k params]
  (let [st @state]
    {:value (parser env query)
     :remote true}))

(defmethod read :app/media-player
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
        (catch js/Object o
          (println (str "Failed to log-that: " o)))))))

(let [known-videos (atom [])]
  (defn vimeo-known-video-reset!
    []
    (reset! known-videos []))
  (defn vimeo-remote
    "Incrementally add videos to the list of available videos"
    [query-maybe cb]
    (go
      (loop [[video & vs] server-video-list
             ui-video-list []
             ui-video-db {}]
        (<! (timeout 2000))
        (let [new-video-id (:video/id video)
              id-set (set (map :video/id @known-videos))]
          (if (contains? id-set new-video-id)
            (if (seq vs) (recur vs ui-video-list ui-video-db))
            (let [new-db (assoc ui-video-db new-video-id video)
                  new-list (conj ui-video-list `[:videos/by-id ~new-video-id])]
              (swap! known-videos conj video)
              (cb {:app/videos new-list
                   :videos/by-id new-db})
              (if (seq vs)
          (recur vs new-list new-db)))))))))

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
  #_(log-this! reconciler "****************************************\nremotes:" map-with-remote-queries
           "\n****************************************")
  (when-let [{:keys [vimeo]} map-with-remote-queries]
    #_(log-this! reconciler "Sending off to vimeo-remote")
    (vimeo-remote vimeo cb)))

(def my-parser
  "Break parser out so I can see it (for development)"
  (om/parser {:read read :mutate mutate}))

;; The Om Reconciler ties state and the parser together
(defonce reconciler
  (om/reconciler {:state app-state :parser my-parser :send my-sender
                  :remotes [:remote]}))

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
  (let [videos (:app/videos (get-the-state reconciler))]
    (if (contains? videos n)
      (om/transact! reconciler `[(do/select-video! {:selected-video ~(videos n)})
                                 :selected-video])
      (println "Video out of range"))))
