# Elytra Trail Plugin (Tested with 1.19.2)

This is a plugin made for 1.19.2 that allows you to add elytra trails using items. 
This effect can be seen in the following GIF.

[![Example of the trail](https://i.gyazo.com/448ac23af3fdd3453f306f90381f07c4.gif)](https://gyazo.com/448ac23af3fdd3453f306f90381f07c4)

This plugin is fully asynchronous and uses packets to send the items and remove them.
This means this will prevent anybody from using hoppers or any of that stuff to try and pickup
the items as the items are literally not on the server just rendered on the client.

[![Image from Gyazo](https://i.gyazo.com/b315e58e0e74bab525de9d20509325e7.png)](https://gyazo.com/b315e58e0e74bab525de9d20509325e7)
[![Image from Gyazo](https://i.gyazo.com/3a75d6b85e1d6e47f8cc4eab9c49a5ff.png)](https://gyazo.com/3a75d6b85e1d6e47f8cc4eab9c49a5ff)
[![Image from Gyazo](https://i.gyazo.com/b3f0bf1d997f30d91fee857bb36d60d8.png)](https://gyazo.com/b3f0bf1d997f30d91fee857bb36d60d8)

This plugin is also very efficient while I was testing it with spark 
(Which is expected when literally nothing is running on the main thread)
[![Image from Gyazo](https://i.gyazo.com/561ce515475b5c8540cc8e12b5c12024.png)](https://gyazo.com/561ce515475b5c8540cc8e12b5c12024)