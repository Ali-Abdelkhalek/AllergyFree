//
//  Item.swift
//  AllergyFree
//
//  Created by Ali Abdelkhalek on 04/01/2026.
//

import Foundation
import SwiftData

@Model
final class Item {
    var timestamp: Date
    
    init(timestamp: Date) {
        self.timestamp = timestamp
    }
}
