# WinVMJ Project AMANAH
This repository consists a FeatureIDE project with WinVMJ composer for AMANAH case study.

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
- Open the project, the structure directory is:
    ```
    .
    ├── src
    ├── configs
    ├── external
    ├── modules
    ├── src-gen
    ├── db.properties
    ├── feature_to_module.json
    ├── FeatureMapping.xml
    ├── inter_spl_product.json
    ├── model.uvl
    ```
- Don't forget to edit file `db.properties` in the project with your PostgreSQL credentials

## Development
WinVMJ framework is designed based on Varibility Modules for Java (VMJ).
VMJ is an architectural pattern in Java to implement software product lines
and multi product lines based on Delta Oriented Programming (DOP).
VMJ combines Java module system and design pattern to implement DOP. 

To implement a new variation:
1. Add a new feature in the feature diagram (model.uvl)
2. Develop a module in the directory `module`.
Naming convention for the folder:
- core module [productlinename].[modulename].core
- delta module [productlinename].[coremodulename].[deltamodulename]
FYI, the core and delta module can be generated from the UML-DOP diagram,
you can also create/update the UML-DOP diagram and generate the source.
See this repository: https://gitlab.com/RSE-Lab-Fasilkom-UI/PricesIDE/uml-to-vmj
3. Define a mapping between feature name and delta name in file `feature_to_module.json`

## Generate and Run Product
### Generate Product
Generate product is started with creating a new configuration:
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
