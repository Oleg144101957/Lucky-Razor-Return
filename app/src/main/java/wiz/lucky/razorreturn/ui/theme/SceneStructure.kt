package wiz.lucky.razorreturn.ui.theme

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import wiz.lucky.razorreturn.RazorViewModel
import wiz.lucky.razorreturn.Targets
import wiz.lucky.razorreturn.ui.theme.scenes.FirstScene
import wiz.lucky.razorreturn.ui.theme.scenes.FourthScene
import wiz.lucky.razorreturn.ui.theme.scenes.SecondScene
import wiz.lucky.razorreturn.ui.theme.scenes.SevensScene
import wiz.lucky.razorreturn.ui.theme.scenes.SixthScene
import wiz.lucky.razorreturn.ui.theme.scenes.ThirdScene


@Composable
fun SceneStructure(vm: RazorViewModel){

    val navigation = rememberNavController()


    NavHost(navController = navigation, startDestination = Targets.First.route){

        composable(route = Targets.First.route){
            FirstScene(navigation, vm)
        }

        composable(route = Targets.Second.route){
            SecondScene(navigation)
        }

        composable(route = Targets.Third.route){
            ThirdScene(navigation)
        }

        composable(route = Targets.Fourth.route){
            FourthScene(navigation)
        }

        composable(route = Targets.Sixth.route){
            SixthScene(navigation)
        }

        composable(route = Targets.Sevens.route){
            SevensScene(navigation)
        }
    }
}