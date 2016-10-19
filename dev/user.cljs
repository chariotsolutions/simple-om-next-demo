(ns user
  (:require
   [krimple-react.shared-definitions :as shared]
   [krimple-react.core :as core]
   [krimple-react.media-player-app :as mpa]
   [om.dom :as dom]
   [om.next :as om :refer-macros [defui]]
   ))

(defn try-query
  "Try out a query against the reconciler's live state"
  ([query]
   (shared/my-parser (:config shared/reconciler)
                     query))
  ([query remote]
   (shared/my-parser {:config shared/reconciler}
                     query
                     remote)))
