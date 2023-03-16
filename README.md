# WinVMJ Project AMANAH
This repository consists a FeatureIDE project with WinVMJ composer for AMANAH case study.
The source code for WinVMJ is copied from https://gitlab.com/RSE-Lab-Fasilkom-UI/PricesIDE/vmj-aisco/-/tree/staging.
The WinVMJ source code (from directory `src`) is placed in directory `modules`.

The AMANAH product line in this branch is also related to Volunteer product line in
https://gitlab.com/RSE-Lab-Fasilkom-UI/PricesIDE/vmj-volunteers 

## Requirements
Install Eclipse and required plugins to run this project:
- Eclipse Modeling Tools  (2020-12): https://www.eclipse.org/downloads/packages/release/2020-12/r/eclipse-modeling-tools
- Plugin FeatureIDE 3.9:  http://featureide.cs.ovgu.de/update/v3/
- Plugin WinVMJ composer: https://amanah.cs.ui.ac.id/priceside/winvmj-composer/updatesite
- Java 11
- PostgreSQL 11

## Getting Started
- Make sure that all requirements installed in your Eclipse
- Clone this repository (AMANAH project) into your directory
- Import your directory into Eclipse workspace (do not include the .git folder)
- Clone Volunteer project from https://gitlab.com/RSE-Lab-Fasilkom-UI/PricesIDE/vmj-volunteers 
- Import Volunteer Project into Eclipse workspace 
- Don't forget to edit file `db.properties` in the project with your PostgreSQL credentials

## Generate and Run Product
### Generate Product
- Generate product is started with creating a new configuration:
1. The configuration is defined in directory `configs`. 
2. Right click on the project -> NEW -> OTHER -> FeatureIDE -> Configuration File
3. Defined the config file's name that represents the product's name
4. Select required features, right click on the config file -> FeatureIDE -> select `Set As Current Configuration`
5. Generated modules are available in directory `src`
6. Compile the generated module: right click on directory `src` -> FeatureIDE -> WinVMJ -> Compile
The generated application is placed in directory `src-gen`

### Run Product
1. Choose Run -> External Tools -> External Tools Configurations. Define the configuration's name.
2. Fill in location of the generated product, e.g., `${workspace_loc:/aisco-winmvj/src-gen/CharitySchool/run.bat}`
3. Fill in the working directory by defining the product's directory, e.g.,`${workspace_loc:/aisco-winmvj/src-gen/CharitySchool}`
4. Click Run
5. If succeed, the product is ready and a list of avalaible endpoints is printed on the console, for example:
```
http://localhost:7776/call/program/list
```
6. Seed initial data for ChartOfAccount and Auth from directory `sql`. Add your google
account to `auth_seed.sql` and grant `Administator` role.
