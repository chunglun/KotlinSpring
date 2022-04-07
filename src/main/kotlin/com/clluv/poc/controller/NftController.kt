package com.clluv.poc.controller

import com.clluv.poc.exception.NFTNotFoundException
import com.clluv.poc.model.NFT
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RequestMapping("/nfts")
@RestController
class NftController {

    @Value("\${company_name}")
    private lateinit var name: String

    private var nfts = mutableListOf(
        NFT(1, "nft1", 100.0),
        NFT(2, "nft2", 36.9),
        NFT(3, "nft3", 0.6),
        NFT(4, "nft4", 1.1),
        NFT(5, "nft5", 2.5),
    )

    @GetMapping("/hello")
    fun sayHello(): String = "Hello World"

    @GetMapping("")
    fun getNFTs() = nfts

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun postNFT(@RequestBody nft: NFT): NFT {
        val maxId = nfts.maxOfOrNull { it.id } ?: 0
        val nextId = maxId + 1
        val newNft = NFT(id = nextId, name = nft.name, floor_price = nft.floor_price)
        nfts.add(newNft)
        return newNft
    }

    @GetMapping("/{id}")
    fun getNFTById(@PathVariable id: Int): NFT? = nfts.firstOrNull { it.id == id } ?: throw NFTNotFoundException()

    @GetMapping("/home")
    fun getHome() = "$name: NFTs Marketplace"
}