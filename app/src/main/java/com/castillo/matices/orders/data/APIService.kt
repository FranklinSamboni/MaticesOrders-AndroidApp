package com.castillo.matices.orders.data

import com.castillo.matices.orders.models.*
import retrofit2.Response
import retrofit2.http.*

interface APIService {

    // Order
    @GET("order/")
    suspend fun getOrders(): Response<DataResponse<List<Order>>>

    @POST("order/")
    suspend fun addOrder(@Body orderRequest: OrderRequest): Response<DataResponse<Order>>

    @PUT("order/")
    suspend fun updateOrder(@Body orderRequest: OrderRequest): Response<DataResponse<Order>>

    @POST("order/addproduct")
    suspend fun addProductToOrder(@Body request: AddProductToOrderRequest): Response<DataResponse<Order>>

    @HTTP(method = "DELETE", path = "order/", hasBody = true)
    suspend fun deleteOrder(@Body orderRequest: OrderRequest): Response<DataResponse<Order>>

    // Client

    @POST("client/")
    suspend fun createClient(@Body clientRequest: ClientRequest): Response<DataResponse<Client>>

    @PUT("client/")
    suspend fun updateClient(@Body clientRequest: ClientRequest): Response<DataResponse<Client>>

    // Product

    @POST("product/")
    suspend fun createProduct(@Body productRequest: ProductRequest): Response<DataResponse<Product>>

    @PUT("product/")
    suspend fun updateProduct(@Body productRequest: ProductRequest): Response<DataResponse<Product>>

    @HTTP(method = "DELETE", path = "product/", hasBody = true)
    suspend fun deleteProduct(@Body productRequest: ProductRequest): Response<DataResponse<Product>>

    // DocumentType

    @GET("identificationType/")
    suspend fun getDocumentTypes(): Response<DataResponse<List<IdentificationType>>>

    // Size

    @GET("size/")
    suspend fun getSizes(): Response<DataResponse<List<Size>>>

    @GET("color/")
    suspend fun getColors(): Response<DataResponse<List<Color>>>

    // Stamp
    @GET("stamp/")
    suspend fun getStamps(): Response<DataResponse<List<Stamp>>>

    // Stamp
    @GET("stamp/")
    suspend fun addOrUpdateStamp(): Response<DataResponse<List<Stamp>>>

    // Stamp
    @GET("stamp/")
    suspend fun deleteStamp(): Response<DataResponse<List<Stamp>>>

}