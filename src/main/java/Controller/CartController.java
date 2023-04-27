package Controller;

import Model.Cart;
import Model.CartItem;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("")
@Controller
public class CartController {

    private Cart cart;


    @GetMapping("/cart")
    public String showCart(Model model, HttpSession session) {

        cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);

            session.setMaxInactiveInterval(30);

            return "cart";
        }

        model.addAttribute("items",  cart.getItems());
        model.addAttribute("total", cart.getTotal());

        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam String name, @RequestParam double price, @RequestParam int quantity) {

        CartItem cartItem = new CartItem(name, price, quantity);
        cart.addItem(cartItem);

        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam int index) {
        cart.removeItem(index);
        return "redirect:/cart";
    }


    @PostMapping("/cart/tøm")
    public String emptyCart(HttpSession session) {
        session.removeAttribute("cart");
        return "redirect:/cart";
    }
}
