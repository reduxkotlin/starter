//
//  ContentView.swift
//  KmpExampleSwiftUi
//
//  Created by Patrick Jackson on 8/8/19.
//  Copyright © 2019 Patrick Jackson. All rights reserved.
//

import SwiftUI
import common
let store = StoreKt.createStore()
struct ContentView: View {
    var body: some View {
        VStack {
            Text("\(AppStateKt.allMovieMenuValues().compactMap({($0 as MoviesMenu).title}).first!)")
            Text("Hello \(store.state!.description())")
        }
    }
}

#if DEBUG
struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
#endif
