import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)

class RestaurantTest {
    Restaurant restaurant;
    LocalTime openingTime = LocalTime.parse("10:30:00");
    LocalTime closingTime = LocalTime.parse("22:00:00");
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,
                closingTime);
        Restaurant spiedRestaurant=Mockito.spy(restaurant);
        LocalTime correctTime=LocalTime.parse("13:00:00");
        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(correctTime);
        assertTrue(spiedRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,
                closingTime);
        Restaurant spiedRestaurant=Mockito.spy(restaurant);
        LocalTime incorrectTime=LocalTime.parse("07:00:00");
        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(incorrectTime);
        assertFalse(spiedRestaurant.isRestaurantOpen());

    }
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    @Test
    public void if_user_selects_no_item_totalPrice_should_be_zero(){
        Restaurant restaurant=new Restaurant("Pumpkin Tales","Chennai", LocalTime.parse("12:00:00"),LocalTime.parse("23:00:00"));
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        restaurant.addToMenu("Alfredo pasta",110);

        ArrayList<String> itemList=new ArrayList<String>();
        assertEquals(0,restaurant.totalPrice(itemList));
    }
    @Test
    public void  if_user_selects_some_items_totalPrice_should_be_non_zero(){
        Restaurant restaurant=new Restaurant("Pumpkin Tales","Chennai", LocalTime.parse("12:00:00"),LocalTime.parse("23:00:00"));
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        restaurant.addToMenu("Alfredo pasta",110);

        ArrayList<String> itemList=new ArrayList<String>();
        itemList.add("Sweet corn soup");
        itemList.add("Alfredo pasta");
        assertEquals(229,restaurant.totalPrice(itemList));
    }


}