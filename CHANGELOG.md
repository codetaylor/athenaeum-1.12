1.13.0
* Added: additional PacketService methods
* Added: null ingredient support to ingredient wrapper
* Changed: BottleHelper methods no longer cause a world block update

1.12.6
* Added: biome registration strategy
* Changed: combined helper package into util package
* Changed: generalized client registration strategy
* Changed: refactored block registry methods

1.11.6
* Added: StackHelper::copyInto method

1.11.5
* Added: StringHelper utility class
* Added: CTInputHelper#getMatchingStacks support for ingredientOr
* Changed: bone material now uses bone oreDict entry as ingredient

1.10.5
* Added: recipe item parser classes from Dropt
* Added: FileHelper utility class
* Changed: material names to use Athenaeum mod id

1.9.5
* Added: EnchantingHelper utility class

1.8.5
* Changed: updated zh_cn.lang (PR#3 DYColdWind)

1.8.4
* Added: new ingredient conversion helper methods
* Added: fr_fr.lang (Okii35)

1.7.4
* Added: gui / container framework

1.6.4
* Fixed: Crash - Updating Screen Events (codetaylor/artisan-worktables #47)
* Changed: updated project to build against latest CraftTweaker builds

1.6.3
* Added: BlockHelper
* Added: BottleHelper
* Added: GuiHelper#drawVerticalScaledTexturedModalRectFromIconAnchorBottomLeft

1.5.3
* Added: moved all reference materials from Artisan Worktables into lib
* Added: bone material

1.4.3
* Added: WeightedPicker util

1.3.3
* Added: SimplePluginHandler and plugin handler registration for GameStages mod

1.2.3
* Added: network wrapper, packet registry, and packet service

1.1.3
* Fixed: duplicate calls to CraftTweaker integration plugin delgate which caused log errors and duplicate recipes

1.1.2
* Fixed: missing setUnlocalizedName() during item registration

1.1.1
* Module integration of registration helpers

1.0.1
* Fixed dependencies
* Fixed crafttweaker plugin application

1.0.0