//
//  MGMRemoteData.h
//  MusicGeekMonthly
//
//  Created by Ceri Hughes on 14/02/2014.
//  Copyright (c) 2014 Ceri Hughes. All rights reserved.
//

#import "MGMData.h"

@interface MGMRemoteData : MGMData

@property (copy) NSString* checksum;

+ (MGMRemoteData*) dataWithError:(NSError*)error;

@end