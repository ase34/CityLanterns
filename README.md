CityLanterns
============

This [Bukkit](http://bukkit.org/) plugin simply switches on selected redstone lamps on if it's night, and switches them off at day.

Please remember that this version may still contain some bugs. ;) If you found any please report them via a PM on Bukkit.org or dev.bukkit.org.


Building
--------
This plugin uses [Maven](http://maven.apache.org/) for automatic building.

Just clone this repo and run `mvn`.

Commands & Permissions
----------------------

The following commands are available:

<dl>
<dt>/citylanternsselect|cls [&lt;group&gt; [we]]</dt>
<dd>
<p>Will either give a tool for adding/removing lanterns to/from the group <code>&lt;group&gt;</code> (Will be set to 'main' if not supplied) or, if the <code>we</code> argument is given, sets the group of all lanterns to <code>&lt;group&gt;</code> in the currently selected <a href="http://dev.bukkit.org/bukkit-plugins/worldedit/">WorldEdit</a> region.</p>
<p>Permission name is <code>citylanterns.select</code> (default for ops)</p>
</dd>
<dt>/citylanternsgroups|clg [&lt;group&gt;]</dt>
<dd>
<p>Lists all groups or, if <code>&lt;group&gt;</code> is given, shows detailed information about that group.</p>
<p>Permission name is <code>citylanterns.groups</code> (default for ops)</p>
</dd>
</dl>


Configuration
-------------

Here's a demo config with description:

Times:
- 0/24000: sunset
- 6000: mid day
- 12000: sunset
- 18000: mid night

After 23999 ticks the counter will be reset to 0 and start counting up again.

```
night_time: 12000 			# Default time (in ticks) for groups when lanterns will toggle on
day_time: 0 				# Default time (in ticks) for groups when lanterns will toggle off
lamps_on_thundering: true	# Should redstone lamps toggle on while it's thundering/raining by default?
toggle_delay: 10			# Delay of toggling the lanterns to prevent lags, in ticks (1 second = 20 ticks)  
```

File Format
-----------

Lantern locations in the worlds are saved in `storage.txt`, found in the plugin folder. 
*[LanternFileStorage#save()](https://github.com/ase34/CityLanterns/blob/master/src/main/java/me/ase34/citylanterns/storage/LanternFileStorage.java#L32)* 
will fill the file with the following structure (applies to v1.9):

<ol>
<li>The magic number <code>0x43 0x4C 0x31</code> (3 bytes, ASCII <em>CL1</em>)</li>
<li>For each group of lanterns:
<ol>
<li>The bytes of the group's name representation by Java's <em><a href="http://docs.oracle.com/javase/1.4.2/docs/api/java/io/DataOutput.html#writeUTF(java.lang.String)">writeUTF</a></em> method</li>
<li>The count of different worlds in the list of lanterns (4 bytes)</li>
<li>For each world in the group:
<ol>
<li>The UUID of the world, first <em><a href="http://docs.oracle.com/javase/6/docs/api/java/util/UUID.html#getMostSignificantBits()">getMostSignificantBits</a></em>, 
then <em><a href="http://docs.oracle.com/javase/6/docs/api/java/util/UUID.html#getLeastSignificantBits()">getLeastSignificantBits</a></em>. (16 bytes in total)</li>
<li>The count of lanterns in that world (4 bytes)</li>
<li>For each location of lanterns in that world:
<ol>
<li>The X-Coordinate (4 bytes)</li>
<li>The Y-Coordinate (4 bytes)</li>
<li>The Z-Coordinate (4 bytes)</li>
</ol>
</li>
</ol>
</li>
</ol>
</li>
</ol>
