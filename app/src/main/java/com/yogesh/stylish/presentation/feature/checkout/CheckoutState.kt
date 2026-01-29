import com.yogesh.stylish.data.local.entity.AddressEntity
import com.yogesh.stylish.domain.model.CartItem

data class CheckoutState(
    val cartItems: List<CartItem> = emptyList(),
    val selectedAddress: AddressEntity? = null,
    val totalOriginalPrice: Int = 0,
    val totalDiscountedPrice: Int = 0,
    val totalSavings: Int = 0,
    val shippingCharge: Int = 0,
    val finalPayable: Int = 0,
    val isLoading: Boolean = false
)