package me.arkty.flickrer.data.remote

import me.arkty.flickrer.data.remote.data.PhotosResponse
import me.arkty.flickrer.data.remote.interceptors.FlickMethod
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {
    //https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=47e19c389f683d0a002602a870b12b89&user_id=61495424@N00&format=json&nojsoncallback=?
    @GET("services/rest/")
    @FlickMethod("flickr.photos.search")
    suspend fun search(@Query("text") query: String): PhotosResponse
}