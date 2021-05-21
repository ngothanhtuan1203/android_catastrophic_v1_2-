package com.tnmobile.catastrophic.utilily

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.lang.reflect.Type

class JsonUtil {
    companion object {
        /**
         * Null serialize is used because else Gson will ignore all null fields.
         */
        private var gson: Gson = GsonBuilder()
            .disableHtmlEscaping()
            .serializeNulls()
            .setPrettyPrinting()
            .create()

        /**
         * Returns the specific object given the Json String
         *
         * @param <T>
         * @param jsonString
         * @param obj
         * @return a specific object as defined by the user calling the method
        </T> */
        fun <T> fromJsonStringToObj(jsonString: String?, obj: Class<T>?): T {
            return gson.fromJson(jsonString, obj)
        }

        /**
         * Returns a list of specified object from the given json array
         * @param <T>
         * @param jsonString
         * @param t the type defined by the user
         * @return a list of specified objects as given in the json array
        </T> */
        fun <T> fromJsonToList(
            jsonString: String?,
            t: Type?
        ): List<T>? {
            return gson.fromJson(jsonString, t)
        }

        /**
         * To Json Converter using Goolge's Gson Package
         *
         *
         * this method converts a simple object to a json string
         *
         * @param obj
         * @return a json string
         */
        fun <T> toJsonString(obj: T): String {
            return gson.toJson(obj)
        }

    }
}