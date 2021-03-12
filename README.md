# Proposition of a project structure

- data
  - model (api models)
  - mapper (mappers from api models to domain models)
  - network (retrofit ApiService.kt, ApiServiceBuilder.kt)
  - repository (where actual requests are made)
- domain
  - model
  - mapper
  - interactor (if viewModel will grow to be huge)
- presentation
  - activity (MainActivity.kt - the only activity in our app)
  - fragment
  - dialog
  - model (if needed)
  - viewmodel (viewModels + Factory)
  - App.kt (this class is entry point of our app : Application())

