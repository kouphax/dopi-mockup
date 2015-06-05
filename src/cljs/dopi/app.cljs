(ns dopi.app
  (:require [reagent.core   :as reagent :refer [atom]]
            [cljs-time.core :as time]
            [dopi.pi :refer [pi]]
            [dopi.localstorage :as ls]))

(def starting-digit-position (atom 0))

(def records (atom { 1 true
                     2 false }))

(defn can-has-actions [position]
  (<= position (inc (apply max (keys @records)))))

(defn day [digit position]
  [:div.day { :class (case (get @records position)
                          true  "complete"
                          false "failed"
                          "") }
   [:div.digit  digit]
     [:div.actions { :class (if (can-has-actions position) "" "hidden")}
      [:a.btn-floating.green.accent-4
       { :on-click #(swap! records assoc position true) }
       [:i.mdi-action-done]]
      [:a.btn-floating.red.accent-4
       { :on-click #(swap! records assoc position false) }
       [:i.mdi-content-clear]]]])

(defn week []
  [:div.week.cf
   (doall
     (let [start @starting-digit-position]
       (for [index (range start (+ start 7))
             :let [digit (nth pi index)]]
          [day digit index]))) ])

#_(js/setInterval
  (fn [] (swap! starting-digit-position inc))
  1000)

(defn init []
  (reagent/render-component [week]
                            (.getElementById js/document "container")))
