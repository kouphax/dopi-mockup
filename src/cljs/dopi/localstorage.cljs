(ns dopi.localstorage)

(defn set-item!
  "Set 'key' in browser's localStorage to 'val'."
  [key val] (.setItem  (.-localStorage js/window) key val))

(defn get-item
  "Returns value of 'key' from browser's localStorage."
  [key] (.getItem  (.-localStorage js/window) key))

(defn remove-item!
  "Remove the browser's localStorage value for the given 'key'"
  [key] (.removeItem  (.-localStorage js/window) key))

(defn set-object!
  [key val] (set-item! key (js/JSON.stringify (clj->js val))))

(defn debug [arg]
  (.log js/console arg)
  arg)

(defn keywordise [o]
  (zipmap (map keyword (keys o)) (vals o)))

(defn get-object
  [key]
  (when-let [item (get-item key)]
    (-> item
        (js/JSON.parse)
        (js->clj)
        (keywordise))))
