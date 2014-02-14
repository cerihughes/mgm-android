//
//  MGMWebsiteAlbumMetadataDao.m
//  MusicGeekMonthly
//
//  Created by Ceri Hughes on 14/02/2014.
//  Copyright (c) 2014 Ceri Hughes. All rights reserved.
//

#import "MGMWebsiteAlbumMetadataDao.h"

@interface MGMWebsiteAlbumMetadataDao ()

@property MGMAlbumServiceType internalServiceType;
@property (copy) NSString* albumUrlPattern;
@property (copy) NSString* searchUrlPattern;

@end

@implementation MGMWebsiteAlbumMetadataDao

- (id) initWithCoreDataDao:(MGMCoreDataDao *)coreDataDao reachabilityManager:(MGMReachabilityManager *)reachabilityManager albumUrlPattern:(NSString*)albumUrlPattern searchUrlPattern:(NSString*)searchUrlPattern serviceType:(MGMAlbumServiceType)serviceType
{
    if (self = [super initWithCoreDataDao:coreDataDao reachabilityManager:reachabilityManager])
    {
        self.albumUrlPattern = albumUrlPattern;
        self.searchUrlPattern = searchUrlPattern;
        self.internalServiceType = serviceType;
    }
    return self;
}

- (MGMAlbumServiceType) serviceType
{
    return self.internalServiceType;
}

- (void) updateAlbumInfo:(MGMAlbum *)album completion:(FETCH_COMPLETION)completion
{
    completion(album, nil);
}

- (NSString*) serviceAvailabilityUrl
{
    return self.albumUrlPattern;
}

- (NSString*) urlForAlbum:(MGMAlbum*)album
{
    NSString* metadata = [album metadataForServiceType:self.internalServiceType];
    if (metadata)
    {
        return [NSString stringWithFormat:self.albumUrlPattern, metadata];
    }

    if (self.searchUrlPattern)
    {
        return [NSString stringWithFormat:self.searchUrlPattern, album.artistName, album.albumName];
    }

    return nil;
}

@end
