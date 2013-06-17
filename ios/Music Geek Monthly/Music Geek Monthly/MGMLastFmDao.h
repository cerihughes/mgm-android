//
//  MGMLastFmDao.h
//  Music Geek Monthly
//
//  Created by Ceri Hughes on 05/06/2013.
//  Copyright (c) 2013 Ceri Hughes. All rights reserved.
//

#import "MGMDao.h"
#import "MGMGroupAlbum.h"

@interface MGMLastFmDao : MGMDao

- (NSArray*) topWeeklyAlbums;
- (void) updateAlbumInfo:(MGMGroupAlbum*)album;

@end