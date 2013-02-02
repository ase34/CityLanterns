CityLanterns
============

This [Bukkit](http://bukkit.org/) plugin simply switches on selected redstone lamps on if it's night, and switches them off at day.

Please remember that this version may still contain some bugs. ;) If you found any please report them to the [issue tracker](https://github.com/ase34/CityLanterns/issues).


Building
--------
This plugin uses [Maven](http://maven.apache.org/) for automatic building.

Just clone this repo and run `mvn`.

Commands & Permissions
----------------------

The following commands are available:

* /selectlanterns, /sl - Toggles the lantern-selection-mode - `citylanterns.sl`

Configuration
-------------

Here's a demo config with description:

Times:
- 0/24000: sunset
- 6000: mid day
- 12000: sunset
- 18000: mid night

After 23999 ticks the counter will be reset to 0 and start counting up again.

    night_time: 12000 	# Time (in ticks) when lanterns will toggle on
    day_time: 0 		# Time (in ticks) when lanterns will toggle off