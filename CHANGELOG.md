# Changelog

## Version v1.2.0 pre-alpha

### Features
Short overview:

  - added full lightmap support
  - added support for blob shadows
  - added support for movement animations
  - added support for texture atlases / atlas animations
  - added support for more than one animation state
  - fixed some problems with lighting system
  - added hover component (experimental!)
  - added component to set other mouse cursor, if entity is hovered (for example for attacks)
  - added full support for drawing texture regions
  - some performance improvements
  - added component for attachment points
  - added actionbar
  - added cooldown timer
  - added support for first action buttons
  - added support for developer mode
  - and some other features

## Version v1.1.0 pre-alpha

### Features
  - added support for lighting in entities (lighting component)
  - added support for shadows
  - added support to draw an texture (also character)
  - added support for basic animations

## Version v1.0.0 pre-alpha

An first pre-alpha of my new game client, based on libGDX game engine.

### Features

Some (technical) features:
  - camera
  - camera shake (3 variants)
  - camera zoom
  - entity component system
  - character movement system
  - game states / game screen system (you can also push more than one game screen at same time, for example HUD is on another screen)
  - extended input system & input mapping
  - small GUI / HUD
    * support for Stages & libGDX Scene2d.ui and custom HUD widgets
    * can load libGDX UI skins
  - shared data between game screens (game screens can share data objects with each other)
  - load screen
  - basic save & load of games
  - lighting system
    * ambient light
    * special lights as lightmap
  - basic story teller (just an beginning)
  - support of more than one sectors
  - main menu
  - game world with tiled maps

Added Screens:
  - intro screen (shows JuKuSoft logo)
  - load screen (for asset loading)
  - main menu
  - create character
  - load character
  - credits
  - game screen (current game)
  - HUD overlay screen

### Special thanks

Specical thanks to:
 - pentaquin.com project
 - libGDX team
 - http://alcove-games.com for nice tutorials
 - http://www.netprogs.com/libgdx-screen-shaking/ for camera shake tutorials
 - Patrick D. for some tips about OpenGL & rendering
 - Christian Greiner (smooth camera tip)