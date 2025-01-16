package machine

fun main() {

    val coffeeMachine = CoffeeMachine()

    coffeeMachine.operate()
}

class CoffeeMachine(
    private var money: Int = STARTING_MONEY,
    private var water: Int = STARTING_WATER,
    private var milk: Int = STARTING_MILK,
    private var coffeeBeans: Int = STARTING_COFFEE_BEANS,
    private var disposableCup: Int = STARTING_DISPOSABLE_CUPS
) {

    private var isFinish = false

    fun operate() {
        while (!isFinish) {
            println(INITIAL_PROMPT)
            val operation = readln()

            when(operation) {
                Operation.BUY.text -> sellCoffee()
                Operation.FILL.text -> fillIngredients()
                Operation.TAKE.text -> withdrawMoney()
                Operation.EXIT.text -> exit()
                Operation.REMAINING.text -> displayStatus()
            }
            println()
        }
    }

    private fun sellCoffee() {
        println(BUY_COFFEE_PROMPT)
        val command = readln()

        if (command != Operation.BACK.text) {
            val drink = when(command.toInt()) {
                ESPRESSO_MENU -> Drink.ESPRESSO
                LATTE_MENU -> Drink.LATTE
                CAPPUCCINO_MENU -> Drink.CAPPUCCINO
                else -> Drink.NULL
            }
            makeCoffee(drink)
        }
    }

    private fun makeCoffee(drink: Drink) {
        when {
            water < drink.water -> println("Sorry, not enough water!")
            milk < drink.milk -> println("Sorry, not enough milk!")
            coffeeBeans < drink.coffee -> println("Sorry, not enough coffee!")
            disposableCup == 0 -> println("Sorry, not enough cup!")
            else -> {
                water -= drink.water
                milk -= drink.milk
                coffeeBeans -= drink.coffee
                money += drink.price
                if (drink != Drink.NULL) disposableCup--
                println("I have enough resources, making you a coffee!")
            }
        }
    }

    private fun fillIngredients() {
        println("Write how many ml of water you want to add: ")
        val addedWater = readln().toInt()
        println("Write how many ml of milk you want to add: ")
        val addedMilk = readln().toInt()
        println("Write how many grams of coffee beans you want to add: ")
        val addedCoffee = readln().toInt()
        println("Write how many disposable cups you want to add: ")
        val addedCup = readln().toInt()

        water += addedWater
        milk += addedMilk
        coffeeBeans += addedCoffee
        disposableCup += addedCup
    }

    private fun withdrawMoney() {
        println("I gave you $$money")
        money = 0
    }

    private fun displayStatus() {
        println("""
            The coffee machine has:
            $water ml of water
            $milk ml of milk
            $coffeeBeans g of coffee beans
            $disposableCup disposable cups
            $$money of money
        """.trimIndent())
    }

    private fun exit() {
        isFinish = true
    }

    companion object {
        const val STARTING_MONEY = 550
        const val STARTING_WATER = 400
        const val STARTING_MILK = 540
        const val STARTING_COFFEE_BEANS = 120
        const val STARTING_DISPOSABLE_CUPS = 9

        const val INITIAL_PROMPT = "Write action (buy, fill, take):"
        const val BUY_COFFEE_PROMPT = "What do you wan to buy? 1 - espresso, 2 - latte, 3 - cappuccino: "

        const val ESPRESSO_MENU = 1
        const val LATTE_MENU = 2
        const val CAPPUCCINO_MENU = 3
    }

    enum class Drink(val water: Int, val coffee: Int, val milk: Int, val price: Int) {
        ESPRESSO(water = 250, coffee = 16, milk = 0, price = 4),
        LATTE(water = 350, coffee = 20, milk = 75, price = 7),
        CAPPUCCINO(water = 200, coffee = 12, milk = 100, price = 6),
        NULL(water = 0, coffee = 0, milk = 0, price = 0)

    }

    enum class Operation(val text: String) {
        BUY("buy"),
        FILL("fill"),
        TAKE("take"),
        EXIT("exit"),
        REMAINING("remaining"),
        BACK("back")
    }
}
