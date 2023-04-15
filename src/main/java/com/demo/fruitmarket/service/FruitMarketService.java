package com.demo.fruitmarket.service;

import com.demo.fruitmarket.config.FruitMarketException;
import com.demo.fruitmarket.controller.requestCommand.PutIntoShoppingCartCommand;
import com.demo.fruitmarket.controller.responseCommand.GetAllFruitAndPriceRepresentation;
import com.demo.fruitmarket.domain.FruitDto;
import com.demo.fruitmarket.domain.ShoppingCartDto;
import com.demo.fruitmarket.entity.FruitPO;
import com.demo.fruitmarket.entity.ShoppingCartPO;
import com.demo.fruitmarket.repository.FruitMarketRepo;
import com.demo.fruitmarket.repository.ShoppingRepo;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 水果市場 Service.
 *
 * @author YeeDer
 * @version 1.0.0
 * @since 2022/11/18 下午 05:29
 **/
@Service
public class FruitMarketService {

    private final FruitMarketRepo fruitMarketRepo;
    private final ShoppingRepo shoppingRepo;
    private final CacheManager cacheManager;

    public FruitMarketService(FruitMarketRepo fruitMarketRepo, ShoppingRepo shoppingRepo, CacheManager cacheManager) {
        this.fruitMarketRepo = fruitMarketRepo;
        this.shoppingRepo = shoppingRepo;
        this.cacheManager = cacheManager;
    }

    /**
     * 業務邏輯 - 列出所有賣的水果跟單價
     *
     * @return List<Fruit>
     */
    public Set<GetAllFruitAndPriceRepresentation> getAllFruitAndPrice() {

        return fruitMarketRepo.findAll().stream().map(
                        fruitPO ->
                                new GetAllFruitAndPriceRepresentation(fruitPO.getName(), fruitPO.getPrice()))
                .collect(Collectors.toSet());
    }

    /**
     * 業務邏輯 - 將東西放進購物車
     *
     * @param command command
     */
    public void putIntoShoppingCart(PutIntoShoppingCartCommand command) throws FruitMarketException {

        /*Step1.查這個水果有沒有販售*/
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto(command);

        /*Step2.有的話拿到水果；沒有就噴錯*/
        FruitPO fruitPO = checkFruitItemExist(shoppingCartDto.getFruitName());
        FruitDto fruitDto = new FruitDto(fruitPO); //PO → DTO

        /*Step3.有的話拿到水果；沒有就噴錯*/
        ShoppingCartPO shoppingCartPO = new ShoppingCartPO(
                shoppingCartDto.getConsumerName(),
                fruitDto.getName(),
                shoppingCartDto.getQuantity()
        );

        /*Step4.先查購物車
         * 有 → 數量相加
         * 沒 → 新增 */
        Optional<ShoppingCartPO> optionalShoppingCartPO = shoppingRepo.findByConsumerName(shoppingCartPO.getConsumerName());
        if (optionalShoppingCartPO.isPresent()) {
            int originalQuantity = optionalShoppingCartPO.get().getQuantity();
            int willPutQuantity = shoppingCartPO.getQuantity();
            shoppingCartPO.setQuantity(originalQuantity + willPutQuantity);
        }

        /*Step5.放進購物車*/
        shoppingRepo.save(shoppingCartPO);
    }


    /**
     * 確認水果有沒有販售
     *
     * @param fruitName fruitName
     * @return FruitPO
     */
    private FruitPO checkFruitItemExist(String fruitName) throws FruitMarketException {
        return fruitMarketRepo
                .findByName(fruitName)
                .orElseThrow(() -> new FruitMarketException("500", "這個水果沒有賣唷", ""));
    }

    /**
     * 購買水果
     *
     * @param buyCount 購買數量
     * @return 購買結果
     */
    public String buyFruit(int buyCount) {

        cacheManager.getCacheNames().forEach(cacheManager -> System.out.println("cacheManager = " + cacheManager));

        Cache applePool = cacheManager.getCache("ApplePool");
        applePool.put("AppleCount", 10);


        int ticket = getTicket();
        System.out.println(ticket);
        setTicketCount(buyCount);
        return "購買成功";
    }

    private int appleCount = 10;

    @Cacheable(cacheNames = "ApplePool")
    public int getTicket() {
        return appleCount;
    }

    @CachePut(value = "ApplePool")
    public void setTicketCount(int count) {
        if (appleCount <= 0) {
            System.out.println("票沒了");
            appleCount += 10;
        } else if (appleCount <= count) {
            System.out.println("票不夠了");
            appleCount += 10;
        } else {
            System.out.println("票還夠");

            appleCount -= count;
        }
    }
}
