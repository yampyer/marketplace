package com.example.meliexample.di

import com.example.meliexample.data.datasources.ProductRemoteDataSource
import com.example.meliexample.data.services.ProductService
import com.example.meliexample.data.repositories.ProductRepositoryImpl
import com.example.meliexample.domain.usecases.GetProductByIdUseCase
import com.example.meliexample.domain.usecases.SearchProductsUseCase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://api.mercadolibre.com/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideProductService(retrofit: Retrofit): ProductService = retrofit.create(ProductService::class.java)

    @Singleton
    @Provides
    fun provideProductRemoteDataSource(productService: ProductService) = ProductRemoteDataSource(productService)

    @Singleton
    @Provides
    fun provideProductRepository(remoteDataSource: ProductRemoteDataSource) =
        ProductRepositoryImpl(remoteDataSource)

    @Provides
    fun provideSearchProductsUseCase(productRepository: ProductRepositoryImpl) =
        SearchProductsUseCase(productRepository)

    @Provides
    fun provideGetProductById(productRepository: ProductRepositoryImpl) =
        GetProductByIdUseCase(productRepository)
}
