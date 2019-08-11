//
//  ContentView.swift
//  KmpExampleSwiftUi
//
//  Created by Patrick Jackson on 8/8/19.
//  Copyright © 2019 Patrick Jackson. All rights reserved.
//

import SwiftUI
import common

struct ContentView: View {
    var body: some View {
        return Text(KmpStarterAppKt.helloWordText)
    }
}

#if DEBUG
struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
#endif
